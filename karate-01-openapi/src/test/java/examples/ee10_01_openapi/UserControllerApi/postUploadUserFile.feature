@openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザーに関連するファイルをアップロードします


  Background:
    * url baseUrl

  @operationId=postUploadUserFile
  Scenario Outline: Test postUploadUserFile for <status> status code

    * def args = read(<testDataFile>)
    * def result = call read('postUploadUserFile.feature@operation') args
    * match result.responseStatus == <status>
    Examples:
      | status | testDataFile                           |
      | 200    | 'test-data/postUploadUserFile_200.yml' |


  @operationId=postUploadUserFile
  Scenario: explore postUploadUserFile inline
    You can use this test to quickly explore your API.
    You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
    * def payload =
      """
      {
        "statusCode": 200,
        "headers": {},
        "params": {
          "id": 0
        },
        "matchResponse": true
      }
      """
    * call read('postUploadUserFile.feature@operation') payload


  @ignore
  @operation @operationId=postUploadUserFile @openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
  Scenario: operation UserControllerApi/postUploadUserFile
    * def args =
      """
      {
      auth: #(karate.get('auth')),
      headers: #(karate.get('headers')),
      params: #(karate.get('params')),
      statusCode: #(karate.get('statusCode')),
      matchResponse: #(karate.get('matchResponse'))
      }
      """
    * def authHeader = call read('classpath:karate-auth.js') args.auth
    * def headers = karate.merge(args.headers || {}, authHeader || {})
    Given path '/api/users/', args.params.id, '/file'
    And headers headers
    And multipart file file = {read:'test-data/FileToUpload.txt', filename:'FileToUpload.txt',Content-type:'mulitpart/form-data'}
    When method POST
    # validate status code
    * if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
    # validate response body
    * if (args.matchResponse === true) karate.call('postUploadUserFile.feature@validate')

  @ignore @validate
  Scenario: validates postUploadUserFile response

    * def responseMatch =
      """
      {
        "ok": "##boolean"
      }
      """
    * match  response contains responseMatch

