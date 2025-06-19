#!/usr/bin/env python3
import argparse
import json
import xml.etree.ElementTree as ET
from datetime import datetime
from azure.cosmos import CosmosClient

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--conn',   required=True, help='Cosmos DB connection string')
    parser.add_argument('--suite',  required=True, help='TestNG suite name')
    args = parser.parse_args()

    try:
        # Connect to CosmosDB
        client = CosmosClient.from_connection_string(args.conn)
        db = client.get_database_client('sonarqubecosmos')
        
        print(f"✅ Connected to database: sonarqubecosmos")
        
        # List all containers in the database
        print("🔍 Available containers:")
        containers = db.list_containers()
        container_names = []
        for container in containers:
            container_name = container['id']
            container_names.append(container_name)
            print(f"  - {container_name}")
        
        # Try to find the right container
        if 'TestResultsDB' in container_names:
            container_name = 'TestResultsDB'
        elif 'Results' in container_names:
            container_name = 'Results'
        elif len(container_names) > 0:
            # Use the first available container
            container_name = container_names[0]
            print(f"⚠️  Using first available container: {container_name}")
        else:
            print("❌ No containers found!")
            return
            
        print(f"🎯 Attempting to use container: {container_name}")
        container = db.get_container_client(container_name)
        
        # Read the TestNG XML results
        xml_file_path = 'target/surefire-reports/testng-results.xml'
        with open(xml_file_path, 'r') as f:
            xml_content = f.read()
        print(f"✅ Successfully read TestNG results from: {xml_file_path}")
        
        # Create a simple test item first
        test_item = {
            'id': f"test_{datetime.now().strftime('%Y%m%d_%H%M%S')}",
            'suite': args.suite,
            'timestamp': datetime.now().isoformat(),
            'message': 'Hello from Jenkins!',
            'testCount': 3
        }
        
        # Insert test item
        result = container.upsert_item(test_item)
        print(f"✅ Successfully pushed test item to CosmosDB!")
        print(f"📊 Document ID: {test_item['id']}")
        
    except Exception as e:
        print(f"❌ Error: {e}")
        raise

if __name__ == '__main__':
    main()
