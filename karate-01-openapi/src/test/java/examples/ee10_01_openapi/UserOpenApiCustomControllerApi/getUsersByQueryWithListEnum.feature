@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を検索します
	クエリーで指定した条件を絞り込み条件として使用します。&lt;br&gt;条件を指定しない場合は全レコードが取得対象となります

Background:
* url baseUrl

@operationId=getUsersByQueryWithListEnum
Scenario Outline: Test getUsersByQueryWithListEnum for <status> status code

	* def params = __row
	* def result = call read('getUsersByQueryWithListEnum.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
	* match result.responseStatus == <status>
		Examples:
		| status | genders | gender | name | matchResponse |
		| 200    | MALE | MALE | fill some value | true          |


@operationId=getUsersByQueryWithListEnum
Scenario: explore getUsersByQueryWithListEnum inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def statusCode = 200
* def params = {"genders":"MALE","gender":"MALE","name":"fill some value"}
* def matchResponse = true
* call read('getUsersByQueryWithListEnum.feature@operation') 


@ignore
@operation @operationId=getUsersByQueryWithListEnum @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserOpenApiCustomControllerApi/getUsersByQueryWithListEnum
* def args = 
"""
{
    auth: #(karate.get('auth')), 
    headers: #(karate.get('headers')), 
    params: #(karate.get('params')), 
    body: #(karate.get('body')), 
    statusCode: #(karate.get('statusCode')), 
    matchResponse: #(karate.get('matchResponse'))
}
"""
* def authHeader = call read('classpath:karate-auth.js') args.auth
* def headers = karate.merge(args.headers || {}, authHeader || {})
Given path '/api/open-api-custom/enumlist'
And param genders = args.params.genders
And param gender = args.params.gender
And param name = args.params.name
And headers headers
When method GET
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
# validate response body
* if (args.matchResponse === true) karate.call('getUsersByQueryWithListEnum.feature@validate')

@ignore @validate
Scenario: validates getUsersByQueryWithListEnum response

* def responseMatch =
"""
{
  "ok": "##boolean"
}
"""
* match  response contains responseMatch

