package com.syntj.skyrim

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Integration test that hits the real powtions.com effects page over the
 * network. Run via `mvn verify` (or `mvn failsafe:integration-test`), since
 * this is excluded from the regular unit test (`mvn test`) run.
 *
 * NOTE: As of writing, https://www.powtions.com/effects is a client-rendered
 * SPA, so the raw HTML fetched by Jsoup does not contain a `<table>` with
 * effect data. This test only asserts the page is reachable until
 * EffectsHtmlClient is updated to pull data from the site's underlying API
 * (or a JS-capable fetcher is used).
 */
class EffectsHtmlClientIT {

    @Test
    fun `fetchEffectsDocument successfully reaches the effects page`() {
        val client = EffectsHtmlClient()

        val document = client.fetchEffectsDocument()

        assertEquals(200, document.connection().response().statusCode())
        assertTrue("Expected a non-empty HTML document", document.html().isNotBlank())
    }
}
