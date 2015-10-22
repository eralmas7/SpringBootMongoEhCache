Scenario: When client tries to push data to repository
 
Given a registered company tries to register chat with user as admin and password as admin
When company tries to send in a new chat with title as New Chat parent as None author as A1234 and content as This is a content
Then user should be allowed to push the data