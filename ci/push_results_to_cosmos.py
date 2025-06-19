#!/usr/bin/env python3
import argparse
import json
import xml.etree.ElementTree as ET
from datetime import datetime
from azure.cosmos import CosmosClient
import os

def parse_testng_results(xml_content):
    """Parse TestNG XML results and extract test information"""
    try:
        root = ET.fromstring(xml_content)
        tests = []
        
        for test_method in root.findall('.//test-method'):
            test_name = test_method.get('name', 'Unknown')
            status = test_method.get('status', 'UNKNOWN')
            class_name = test_method.get('class-name', 'Unknown')
            started_at = test_method.get('started-at', '')
            finished_at = test_method.get('finished-at', '')
            duration_ms = test_method.get('duration-ms', '0')
            
            tests.append({
                'testName': test_name,
                'className': class_name,
                'status': status,
                'startedAt': started_at,
                'finishedAt': finished_at,
                'durationMs': int(duration_ms) if duration_ms.isdigit() else 0
            })
        
        # Extract summary information
        summary = {
            'total': int(root.get('total', 0)),
            'passed': int(root.get('passed', 0)),
            'failed': int(root.get('failed', 0)),
            'skipped': int(root.get('skipped', 0))
        }
        
        return tests, summary
    except Exception as e:
        print(f"Error parsing TestNG XML: {e}")
        return [], {}

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--conn', required=True, help='Cosmos DB connection string')
    parser.add_argument('--suite', required=True, help='TestNG suite name')
    parser.add_argument('--test-type', default='regression', help='Type of test (functional, e2e, smoke, etc.)')
    args = parser.parse_args()

    try:
        # Connect to CosmosDB with the correct database name
        client = CosmosClient.from_connection_string(args.conn)
        db = client.get_database_client('TestResultsDB')
        
        print(f"✅ Connected to database: TestResultsDB")

        # List containers and use the first available one
        containers = list(db.list_containers())
        if not containers:
            print("❌ No containers found in TestResultsDB!")
            return
            
        container_name = containers[0]['id']
        print(f"🎯 Using container: {container_name}")
        container = db.get_container_client(container_name)

        # Read the TestNG XML results
        xml_file_path = 'target/surefire-reports/testng-results.xml'
        try:
            with open(xml_file_path, 'r') as f:
                xml_content = f.read()
            print(f"✅ Successfully read TestNG results from: {xml_file_path}")
        except FileNotFoundError:
            print(f"❌ TestNG results file not found: {xml_file_path}")
            return
        
        # Parse the XML to extract structured test data
        test_results, summary = parse_testng_results(xml_content)
        
        # Get Jenkins environment information
        build_number = os.environ.get('BUILD_NUMBER', 'unknown')
        build_url = os.environ.get('BUILD_URL', '')
        job_name = os.environ.get('JOB_NAME', 'unknown')
        
        # Create the item to store in CosmosDB
        item = {
            'id': f"{args.test_type}_{args.suite}_{datetime.now().strftime('%Y%m%d_%H%M%S')}",
            'testType': args.test_type,
            'suite': args.suite,
            'timestamp': datetime.now().isoformat(),
            'buildInfo': {
                'buildNumber': build_number,
                'buildUrl': build_url,
                'jobName': job_name
            },
            'summary': summary,
            'testCount': len(test_results),
            'tests': test_results,
            'rawXml': xml_content
        }
        
        # Insert into CosmosDB
        result = container.upsert_item(item)
        print(f"🎉 Successfully pushed {len(test_results)} {args.test_type} test results to CosmosDB!")
        print(f"📊 Database: TestResultsDB")
        print(f"📊 Container: {container_name}")
        print(f"📊 Document ID: {item['id']}")
        print(f"📊 Test Type: {args.test_type}")
        print(f"📊 Summary: {summary['passed']} passed, {summary['failed']} failed, {summary['skipped']} skipped")
        
    except Exception as e:
        print(f"❌ Error pushing to CosmosDB: {e}")
        raise

if __name__ == '__main__':
    main()
