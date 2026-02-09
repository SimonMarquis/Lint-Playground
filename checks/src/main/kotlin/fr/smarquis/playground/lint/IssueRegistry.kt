package fr.smarquis.playground.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

internal class IssueRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API
    override val minApi: Int = CURRENT_API

    override val issues: List<Issue> = listOf(
        ReplaceMethodCallDetector.ISSUE,
    )

    override val vendor: Vendor = Vendor(
        vendorName = "Simon Marquis",
        feedbackUrl = "https://github.com/SimonMarquis/Lint-Playground/issues",
        contact = "https://github.com/SimonMarquis/Lint-Playground",
    )

}
