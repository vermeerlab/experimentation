package ee.sample.apps.common.presentation.openapi;

import ee.sample.apps.context.user.domain.Gender;
import ee.sample.plugin.openapi.OpenApiSchemaUtil;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.security.SecurityScheme;
import org.eclipse.microprofile.openapi.models.security.SecurityScheme.In;

/** OpenAPIのOASModelReaderの実装. */
public class OpenApiModelReader implements OASModelReader {

  @Override
  public OpenAPI buildModel() {

    var license =
        OASFactory.createLicense()
            .name("MIT license / Copyright (c) 2024 YAMASHITA Takahiro")
            .url("https://opensource.org/licenses/mit-license.php");

    var info = OASFactory.createInfo().license(license);

    var externalDocs =
        OASFactory.createExternalDocumentation()
            .description("externalDocs.description()")
            .url("https://github.com/vermeerlab/experimentation");

    var securityRequirement = OASFactory.createSecurityRequirement().addScheme("access_token");

    var securityScheme =
        OASFactory.createSecurityScheme()
            .bearerFormat("JWT")
            .description("JWTトークン")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .in(In.HEADER);

    var components =
        OASFactory.createComponents()
            .addSecurityScheme("access_token", securityScheme)
            .addSchema("Gender", OpenApiSchemaUtil.createEnumSchema(Gender.class))
            .addSchema("Genders", OpenApiSchemaUtil.createEnumListSchema(Gender.class))
            .addSchema("UploadFile", OpenApiSchemaUtil.createUploadFileSchema())
            .addSchema("UploadFiles", OpenApiSchemaUtil.createUploadFileListSchema());

    var openApi = OASFactory.createOpenAPI();
    openApi
        .info(info)
        .externalDocs(externalDocs)
        .addSecurityRequirement(securityRequirement)
        .components(components);
    return openApi;
  }
}
