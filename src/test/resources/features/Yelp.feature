Feature: Search Yelp

  Background:
    Given user navigates to https://www.yelp.com
    And selects find Restaurants

    Scenario Outline: Detail of the first restaurant search result
      Given User search restaurant <type>
      When filters by <neighborhood>
      And select first search results
      Then User views restaurant information

      Examples:
      | type  | neighborhood |
      | Pizza | Glen Park    |
      #| Fish  | Glen Park    |
