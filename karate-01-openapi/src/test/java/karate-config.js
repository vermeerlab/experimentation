function fn() {
    var env = karate.env; // get system property 'karate.env'
    karate.log('karate.env system property was:', env);
    if (!env) {
        env = 'dev';
    }

    var port = karate.properties['port'];
    karate.log('url port was:', port);
    if (!port) {
        port = '8081';
    }

    var contextPath = karate.properties['contextPath'];
    karate.log('url contextPath was:', contextPath);
    if (!contextPath) {
        contextPath = 'ee10-01-openapi';
    }

    var config = {
        env: env,
        baseUrl: `http://localhost:${port}/${contextPath}`,
        auth: 'test'
    }
    if (env === 'dev') {
        // customize
        // e.g. config.foo = 'bar';
    } else if (env === 'e2e') {
        // customize
    }
    return config;
}