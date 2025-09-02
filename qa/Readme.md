# OneBrick Dashboard Test Automation

Automated testing suite for OneBrick Dashboard using Robot Framework.

## 🚀 Quick Start

### Prerequisites
- Python 3.11+
- Chrome browser installed

### Run
# Run only bug validation tests
robot --include bug --outputdir results tests/

# Run only UI tests  
robot --include ui --outputdir results tests/

# Run specific test case
robot --test "TC003*" --outputdir results tests/