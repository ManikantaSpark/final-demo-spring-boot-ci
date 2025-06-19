#!/usr/bin/env python3
import argparse
from azure.cosmos import CosmosClient

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--conn',   required=True, help='Cosmos DB connection string')
    parser.add_argument('--suite',  required=True, help='TestNG suite name')
    args = parser.parse_args()

    client    = CosmosClient.from_connection_string(args.conn)
    db        = client.get_database_client('TestResultsDB')
    container = db.get_container_client('Suites')

    # Read the TestNG XML
    with open('target/surefire-reports/testng-results.xml', 'r') as f:
        xml = f.read()

    item = {
        'id':      args.suite,
        'suite':   args.suite,
        'results': xml
    }
    container.upsert_item(item)
    print(f"Pushed results for {args.suite} to CosmosDB.")

if __name__ == '__main__':
    main()
