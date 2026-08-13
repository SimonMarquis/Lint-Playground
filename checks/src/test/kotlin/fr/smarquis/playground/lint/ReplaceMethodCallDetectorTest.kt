package fr.smarquis.playground.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReplaceMethodCallDetectorTest : LintDetectorTest() {

    override fun getDetector() = ReplaceMethodCallDetector()
    override fun getIssues() = mutableListOf(ReplaceMethodCallDetector.ISSUE)

    @Test
    fun test() = lint()
        .files(
            kotlin(
                """
                fun foo() = Unit
                fun main(): Unit = foo()
                """,
            ),
        )
        .run()
        .expect(
            """
            src/test.kt:3: Warning: The method foo() should not be called! [ReplaceMethodCall]
                            fun main(): Unit = foo()
                                               ~~~~~
            0 errors, 1 warnings
            """.trimIndent(),
        )
        .expectSarif(
            """
            {
                "＄schema" : "https://docs.oasis-open.org/sarif/sarif/v2.1.0/errata01/os/schemas/sarif-schema-2.1.0.json",
                "version" : "2.1.0",
                "runs" : [
                    {
                        "tool": {
                            "driver": {
                                "name": "Android Lint",
                                "fullName": "Android Lint (in test)",
                                "version": "1.0",
                                "organization": "Google",
                                "informationUri": "https://developer.android.com/studio/write/lint",
                                "fullDescription": {
                                    "text": "Static analysis originally for Android source code but now performing general analysis"
                                },
                                "language": "en-US",
                                "rules": [
                                    {
                                        "id": "ReplaceMethodCall",
                                        "shortDescription": {
                                            "text": "The method foo() should not be called!",
                                            "markdown": "The method `foo()` should not be called!"
                                        },
                                        "fullDescription": {
                                            "text": "The method foo() should not be called!",
                                            "markdown": "The method `foo()` should not be called!"
                                        },
                                        "defaultConfiguration": {
                                            "level": "warning",
                                            "rank": 50
                                        },
                                        "properties": {
                                            "tags": [
                                                "Correctness"
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        "originalUriBaseIds": {
                            "%SRCROOT%": {
                                "uri": "file://app"
                            }
                        },
                        "results": [
                            {
                                "ruleId": "ReplaceMethodCall",
                                "ruleIndex": 0,
                                "message": {
                                    "text": "The method foo() should not be called!",
                                    "markdown": "The method `foo()` should not be called!"
                                },
                                "locations": [
                                    {
                                        "physicalLocation": {
                                            "artifactLocation": {
                                                "uriBaseId": "%SRCROOT%",
                                                "uri": "src/test.kt"
                                            },
                                            "region": {
                                                "startLine": 3,
                                                "startColumn": 36,
                                                "endLine": 3,
                                                "endColumn": 41,
                                                "charOffset": 69,
                                                "charLength": 5,
                                                "snippet": {
                                                    "text": "foo()"
                                                }
                                            },
                                            "contextRegion": {
                                                "startLine": 2,
                                                "endLine": 4,
                                                "snippet": {
                                                    "text": "\n                fun foo() = Unit\n                fun main(): Unit = foo()\n                "
                                                }
                                            }
                                        }
                                    }
                                ],
                                "fixes": [
                                    {
                                        "description": {
                                            "text": "Replace foo with Unit",
                                            "markdown": "Replace `foo` with `Unit`"
                                        },
                                        "artifactChanges": [
                                            {
                                                "artifactLocation": {
                                                    "uriBaseId": "%SRCROOT%",
                                                    "uri": "src/test.kt"
                                                },
                                                "replacements": [
                                                    {
                                                        "deletedRegion": {
                                                            "startLine": 3,
                                                            "startColumn": 36,
                                                            "charOffset": 69,
                                                            "endLine": 3,
                                                            "endColumn": 41,
                                                            "charLength": 5
                                                        },
                                                        "insertedContent": {
                                                            "text": "Unit\n"
                                                        }
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                ],
                                "partialFingerprints": {
                                    "sourceContext/v1": "2d0b7ffedb079107"
                                }
                            }
                        ]
                    }
                ]
            }
            """.trimIndent(),
        )
        .expectFixDiffs(
            """
            Autofix for src/test.kt line 3: Replace `foo` with `Unit`:
            @@ -3 +3 @@
            -                fun main(): Unit = foo()
            +                fun main(): Unit = Unit
            Data for src/test.kt line 3:   key : value
            """.trimIndent(),
        )
        .cleanup()

}

