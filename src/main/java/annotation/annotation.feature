Feature: annotation

Background: 

Scenario: Search Amzon site for results
   Given User opened Amazon home page "http://www.amazon.com"
   When I enter search text as "Nikon"
   And I sorted results from highest price to slowest
   Then Second product topic should contains text "Nikon D3X"