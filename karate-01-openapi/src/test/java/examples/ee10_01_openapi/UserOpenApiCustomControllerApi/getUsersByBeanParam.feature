@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を検索します（BeanParamを使用）
	クエリーで指定した条件を絞り込み条件として使用します。&lt;br&gt;条件を指定しない場合は全レコードが取得対象となります。&lt;br&gt;

Background:
* url baseUrl

@operationId=getUsersByBeanParam
Scenario Outline: Test getUsersByBeanParam for <status> status code

	* def params = __row
	* def result = call read('getUsersByBeanParam.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
	* match result.responseStatus == <status>
		Examples:
		| status | gender | name | matchResponse |
		| 200    | MALE | User Name | true          |


@operationId=getUsersByBeanParam
Scenario: explore getUsersByBeanParam inline
	You can use this test to quickly explore your API.
	You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
* def statusCode = 200
* def params = {"gender":"MALE","name":"User Name"}
* def matchResponse = true
* call read('getUsersByBeanParam.feature@operation') 


@ignore
@operation @operationId=getUsersByBeanParam @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Scenario: operation UserOpenApiCustomControllerApi/getUsersByBeanParam
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
Given path '/api/open-api-custom/beamparam'
And param gender = args.params.gender
And param name = args.params.name
And headers headers
When method GET
# validate status code
* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
# validate response body
* if (args.matchResponse === true) karate.call('getUsersByBeanParam.feature@validate')

@ignore
@validate
Scenario: validates getUsersByBeanParam response

* def responseMatch =
"""
{
  "ok": true
}
"""
* match  response contains responseMatch

