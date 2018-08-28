package es.voghdev.hellokotlin

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec

class ExampleKotlinTest : StringSpec(
    {
        "length should return the size of string" {
            "hello".length shouldBe 5
        }

        "another simple test to see if KotlinTest works should pass" {
            "ok" shouldNotBe null
        }
    }
)
