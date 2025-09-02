#!/usr/bin/env python3
"""
OneBrick Dashboard Test Runner
Simple Python script to run Robot Framework tests
"""

import os
import sys
import subprocess
from datetime import datetime

def run_tests():
    # Create results directory if not exists
    results_dir = "results"
    if not os.path.exists(results_dir):
        os.makedirs(results_dir)
    
    # Generate timestamp for unique report names
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    
    # Robot Framework command
    cmd = [
        "robot",
        "--outputdir", f"{results_dir}",
        "--output", f"output_{timestamp}.xml",
        "--log", f"log_{timestamp}.html", 
        "--report", f"report_{timestamp}.html",
        "--loglevel", "INFO",
        "tests/onebrick_dashboard_tests.robot"
    ]
    
    print("🤖 Starting OneBrick Dashboard Tests...")
    print(f"📊 Results will be saved in: {results_dir}/")
    print("=" * 50)
    
    try:
        result = subprocess.run(cmd, check=False)
        
        print("=" * 50)
        if result.returncode == 0:
            print("✅ All tests passed!")
        else:
            print("❌ Some tests failed. Check the report for details.")
            
        print(f"📋 Open {results_dir}/report_{timestamp}.html to view detailed results")
        
        return result.returncode
        
    except FileNotFoundError:
        print("❌ Error: Robot Framework not found!")
        print("Please install it with: pip install -r requirements.txt")
        return 1
    except Exception as e:
        print(f"❌ Error running tests: {e}")
        return 1

if __name__ == "__main__":
    sys.exit(run_tests())