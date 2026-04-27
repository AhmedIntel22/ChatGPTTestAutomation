Feature: Google Search Functionality

  Scenario: User searches for a valid keyword
    Given User is on Google homepage
    When User enters keyword "ChatGPT" and presses Enter
    Then Search results for "ChatGPT" are displayed

  Scenario: User searches for a non-existent keyword
    Given User is on Google homepage
    When User enters keyword "zxywqv123456789" and presses Enter
    Then Search results for "zxywqv123456789" are displayed



