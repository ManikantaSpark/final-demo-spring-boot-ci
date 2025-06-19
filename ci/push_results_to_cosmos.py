#!/usr/bin/env python3
import argparse
from azure.cosmos import CosmosClient

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--conn',   required=True, help='Cosmos DB connection string')
    parser.add_argument('--suite',  required=True, help='TestNG suite name')
    args = parser.parse_args()

    try:
        # Connect to CosmosDB
        client = CosmosClient.from_connection_string(args.conn)
        
        print("✅ Connected to CosmosDB successfully")
        
        # List all databases
        print("🔍 Available databases:")
        databases = client.list_databases()
        database_names = []
        for database in databases:
            db_name = database['id']
            database_names.append(db_name)
            print(f"  - {db_name}")
        
        if not database_names:
            print("❌ No databases found!")
            return
            
        # Try to find the right database
        if 'sonarqubecosmos' in database_names:
            target_db = 'sonarqubecosmos'
        else:
            target_db = database_names[0]  # Use first available
            print(f"⚠️ 'sonarqubecosmos' not found, using: {target_db}")
            
        print(f"🎯 Using database: {target_db}")
        db = client.get_database_client(target_db)
        
        # Now list containers
        print("🔍 Available containers:")
        containers = db.list_containers()
        container_names = []
        for container in containers:
            container_name = container['id']
            container_names.append(container_name)
            print(f"  - {container_name}")
            
        if not container_names:
            print("❌ No containers found!")
            return
            
        # Use first available container
        container_name = container_names[0]
        print(f"🎯 Using container: {container_name}")
        container = db.get_container_client(container_name)
        
        # Create a simple test item
        from datetime import datetime
        test_item = {
            'id': f"test_{datetime.now().strftime('%Y%m%d_%H%M%S')}",
            'suite': args.suite,
            'timestamp': datetime.now().isoformat(),
            'message': 'Hello from Jenkins!',
            'testCount': 3
        }
        
        # Insert test item
        result = container.upsert_item(test_item)
        print(f"✅ SUCCESS! Test item inserted into CosmosDB!")
        print(f"📊 Database: {target_db}")
        print(f"📊 Container: {container_name}")
        print(f"📊 Document ID: {test_item['id']}")
        
    except Exception as e:
        print(f"❌ Error: {e}")
        raise

if __name__ == '__main__':
    main()
