*** Variables ***
# Application URL
${URL}                  https://app.onebrick.io/

# Browser Settings
${BROWSER}              Chrome
${DELAY}                2s

# Login Page Elements (SESUAIKAN DENGAN HALAMAN ASLI)
${LOGIN_EMAIL_INPUT}    id:email
${LOGIN_PASSWORD_INPUT} id:password  
${LOGIN_BUTTON}         xpath://button[contains(text(),'Login') or contains(text(),'Login')]
${SIGNUP_LINK}          xpath://a[contains(text(),'Sign Up') or contains(text(),'Register')]

# Signup Page Elements (SESUAIKAN DENGAN HALAMAN ASLI)
${SIGNUP_EMAIL_INPUT}   id:email
${SIGNUP_PASSWORD_INPUT} id:password
${SIGNUP_CONFIRM_PASSWORD} id:confirmPassword
${SIGNUP_FULLNAME_INPUT} id:fullName
${SIGNUP_BUTTON}        xpath://button[contains(text(),'Sign Up') or contains(text(),'Register')]
${LOGIN_LINK}           xpath://a[contains(text(),'Login') or contains(text(),'Sign In')]

# Error Message Elements
${ERROR_MESSAGE}        id:email-text
# ${ERROR_MESSAGE}        xpath://*[contains(@class,'error') or contains(@class,'alert')]
${EMAIL_ERROR}          xpath://*[contains(text(),'email') and contains(@class,'error')]
${PASSWORD_ERROR}       xpath://*[contains(text(),'password') and contains(@class,'error')]

# Test Data
${VALID_EMAIL}          test@example.com
${VALID_PASSWORD}       TestPassword123!
${INVALID_EMAIL}        invalid-email
${SHORT_PASSWORD}       123