plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

val apiVersion = "v1"
val apiSpec: Configuration by configurations.creating
val apiSpecVersion: String by project

dependencies {
    val jacksonVersion: String by project
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    testImplementation(kotlin("test-junit"))
}

/**
 * Указываем место куда будут сгенерированы модели
 */
sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

/**
 * Настраиваем генерацию здесь
 */
openApiGenerate {
    val openapiGroup = "${rootProject.group}.api.$apiVersion"
    generatorName.set("kotlin") // Это и есть активный генератор (клиентский)
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/specs/spec-todos-$apiVersion.yaml") //место спецификации

    /**
     * Здесь указываем, что нам нужны только модели, все остальное не нужно
     * https://openapi-generator.tech/docs/globals
     */
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    /**
     * Настройка дополнительных параметров из документации по генератору
     * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
     */
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

/**
 * Указываем, где будет размещаться базовая спецификация
 */
//val getSpecs by tasks.creating {
//    doFirst {
//        copy {
//            from(apiSpec.asPath)
////            into("$buildDir")
//            into("$rootDir/specs")
//            rename { "base.yaml" }
//        }
//    }
//}

/**
 * Указываем, что модели генерятся до компиляции, те сначала генерируем модели, потом компилируем код
 */
tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}

//tasks {
//    this.openApiGenerate {
//        dependsOn(getSpecs)
//    }
//}

afterEvaluate {
    val openApiGenerate = tasks.getByName("openApiGenerate")
    tasks.filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerate)
    }
    tasks.filter { it.name.endsWith("Elements") }.forEach {
        it.dependsOn(openApiGenerate)
    }
}