#!/usr/bin/env python3
import argparse
import json
import xml.etree.ElementTree as ET
from datetime import datetime
from azure.cosmos import CosmosClient

def parse_testng_results(xml_content):
    """Parse TestNG XML results and extract test information"""
    try:
        root = ET.fromstring(xml_content)
        tests = []
        
        # Find all test methods
        for test_method in root.findall('.//test-method'):
            test_name = test_method.get('name', 'Unknown')
            status = test_method.get('status', 'UNKNOWN')
            class_name = test_method.get('class-name', 'Unknown')
            
            # Get timing information
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
        
        return tests
    except Exception as e:
        print(f"Error parsing TestNG XML: {e}")
        return []

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--conn',   required=True, help='Cosmos DB connection string')
    parser.add_argument('--suite',  required=True, help='TestNG suite name')
    args = parser.parse_args()

    try:
        # Connect to CosmosDB with correct database and container names
        client = CosmosClient.from_connection_string(args.conn)
        db = client.get_database_client('sonarqubecosmos')  # Fixed database name
        container = db.get_container_client('TestResultsDB') # Fixed container name
        
        print(f"Connected to database: sonarqubecosmos, container: TestResultsDB")

        # Read the TestNG XML results
        xml_file_path = 'target/surefire-reports/testng-results.xml'
        try:
            with open(xml_file_path, 'r') as f:
                xml_content = f.read()
            print(f"Successfully read TestNG results from: {xml_file_path}")
        except FileNotFoundError:
            print(f"TestNG results file not found: {xml_file_path}")
            return
        
        # Parse the XML to extract structured test data
        test_results = parse_testng_results(xml_content)
        
        # Create the item to store in CosmosDB
        item = {
            'id': f"{args.suite}_{datetime.now().isoformat()}",  # Unique ID with timestamp
            'suite': args.suite,
            'timestamp': datetime.now().isoformat(),
            'testCount': len(test_results),
            'tests': test_results,
            'rawXml': xml_content  # Keep the original XML for reference
        }
        
        # Insert into CosmosDB
        result = container.upsert_item(item)
        print(f"✅ Successfully pushed {len(test_results)} test results for suite '{args.suite}' to CosmosDB!")
        print(f"📊 Document ID: {item['id']}")
        
    except Exception as e:
        print(f"❌ Error pushing to CosmosDB: {e}")
        raise

if __name__ == '__main__':
    main()
