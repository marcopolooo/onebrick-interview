*** Settings ***
Documentation    Test suite for OneBrick Dashboard - Registration and Login
Library          SeleniumLibrary
Resource         ../resources/keywords/common_keywords.robot
Resource         ../resources/variables/test_data.robot
Test Setup       Open Browser To Dashboard
Test Teardown    Close Browser
Test Tags        onebrick

*** Test Cases ***
TC001 - Verify Dashboard Page Loads
    [Documentation]    Verify that the dashboard page loads successfully
    [Tags]    smoke
    Page Should Contain    OneBrick
    Location Should Be     ${URL}

TC002 - Verify Login Page Elements
    [Documentation]    Verify all required elements are present on login page
    [Tags]    ui
    Go To Login Page
    Page Should Contain Element    ${LOGIN_EMAIL_INPUT}
    Page Should Contain Element    ${LOGIN_PASSWORD_INPUT}
    Page Should Contain Element    ${LOGIN_BUTTON}

TC003 - Login with Empty Email Field
    [Documentation]    Verify validation when email field is empty
    [Tags]    validation    negative    bug
    Go To Login Page
    Clear Element Text    ${LOGIN_EMAIL_INPUT}
    Input Text    ${LOGIN_PASSWORD_INPUT}    ${VALID_PASSWORD}
    Click Button    ${LOGIN_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    email

TC004 - Login with Empty Password Field
    [Documentation]    Verify validation when password field is empty
    [Tags]    validation    negative    bug
    Go To Login Page
    Input Text    ${LOGIN_EMAIL_INPUT}    ${VALID_EMAIL}
    Clear Element Text    ${LOGIN_PASSWORD_INPUT}
    Click Button    ${LOGIN_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    password

TC005 - Login with Both Fields Empty
    [Documentation]    Verify validation when both email and password are empty
    [Tags]    validation    negative    bug
    Go To Login Page
    Clear Element Text    ${LOGIN_EMAIL_INPUT}
    Clear Element Text    ${LOGIN_PASSWORD_INPUT}
    Click Button    ${LOGIN_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    required

TC006 - Login with Invalid Email Format
    [Documentation]    Verify validation for invalid email format
    [Tags]    validation    negative    bug
    Go To Login Page
    Input Text    ${LOGIN_EMAIL_INPUT}    ${INVALID_EMAIL}
    Input Text    ${LOGIN_PASSWORD_INPUT}    ${VALID_PASSWORD}
    Click Button    ${LOGIN_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    email

TC007 - Navigate to Sign Up Page
    [Documentation]    Verify navigation to sign up page works
    [Tags]    navigation
    Go To Login Page
    Click Element    ${SIGNUP_LINK}
    Wait Until Page Contains    Sign Up    timeout=10s

TC008 - Verify Sign Up Page Elements
    [Documentation]    Verify all required elements are present on sign up page
    [Tags]    ui
    Go To Signup Page
    Page Should Contain Element    ${SIGNUP_EMAIL_INPUT}
    Page Should Contain Element    ${SIGNUP_PASSWORD_INPUT}
    Page Should Contain Element    ${SIGNUP_BUTTON}

TC009 - Sign Up with Empty Email Field
    [Documentation]    Verify validation when email field is empty in sign up
    [Tags]    validation    negative    bug
    Go To Signup Page
    Clear Element Text    ${SIGNUP_EMAIL_INPUT}
    Input Text    ${SIGNUP_PASSWORD_INPUT}    ${VALID_PASSWORD}
    Click Button    ${SIGNUP_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    email

TC010 - Sign Up with Empty Password Field
    [Documentation]    Verify validation when password field is empty in sign up
    [Tags]    validation    negative    bug
    Go To Signup Page
    Input Text    ${SIGNUP_EMAIL_INPUT}    ${VALID_EMAIL}
    Clear Element Text    ${SIGNUP_PASSWORD_INPUT}
    Click Button    ${SIGNUP_BUTTON}
    Wait Until Page Contains Element    ${ERROR_MESSAGE}    timeout=10s
    Page Should Contain    password