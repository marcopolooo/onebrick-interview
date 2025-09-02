*** Settings ***
Library    SeleniumLibrary

*** Keywords ***
Open Browser To Dashboard
    [Documentation]    Open browser and navigate to dashboard
    Open Browser    ${URL}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Wait Until Page Contains    OneBrick    timeout=15s

Go To Login Page
    [Documentation]    Navigate to login page
    Go To    ${URL}
    Wait Until Page Contains Element    ${LOGIN_EMAIL_INPUT}    timeout=10s

Go To Signup Page
    [Documentation]    Navigate to sign up page
    Go To Login Page
    Click Element    ${SIGNUP_LINK}
    Wait Until Page Contains Element    ${SIGNUP_EMAIL_INPUT}    timeout=10s

Take Screenshot On Failure
    [Documentation]    Take screenshot when test fails
    Run Keyword If Test Failed    Capture Page Screenshot    failure-{index}.png