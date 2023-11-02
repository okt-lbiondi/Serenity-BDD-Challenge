Feature: Sauce Demo

  Scenario: Login successfully
    Given user navigates to https://www.saucedemo.com/
    When user logins with standard_user and secret_sauce
    Then user should be logged in successfully

      Scenario: Go to about page
        When user goes to about page
        Then user validates about page
        And user navigates back to previous page

      Scenario: Order products by options available
        When user orders products by Price (high to low)
        Then user validates products are ordered by Price (high to low)

      Scenario: Buy the first 4 products
        When user adds the first 4 products to cart
        Then user verifies 4 products were added to cart
        Given user starts checkout process
        When user fills personal data to continue
        Then user verifies price is correct
        When user finishes checkout
        Then user verifies checkout was complete

