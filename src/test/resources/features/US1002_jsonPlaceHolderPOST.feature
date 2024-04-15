@API
Feature: US1002 Kullanici JPH endpoint'ine Post Request gonderir

  @Api2 @smoke @regression
  Scenario: TC02 Kullanici Post request sonucu donen response'i test eder

    Given Kullanici "jPHBaseUrl" base URL'ini kullanir
    Then Path parametreleri icin "posts/70" kullanir
    And POST request icin "Ahmet","Merhaba",10 70 bilgileri ile request body olusturur
    And jPH server a POST request gonderir ve testleri yapmak icin response degerini kaydeder
    Then jPH respons'da status degerinin 200
    And jPH respons'da content type degerinin "application/json; charset=utf-8"
    And jPH respons daki "Connection" header degerinin "keep-alive"
    Then response attributelerindeki degerlerin "Ahmet","Merhaba",10 ve 70