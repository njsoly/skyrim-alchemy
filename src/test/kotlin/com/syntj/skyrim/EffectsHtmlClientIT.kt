package com.syntj.skyrim

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Integration test that hits the real powtions.com effects page over the
 * network. Run via `mvn verify` (or `mvn failsafe:integration-test`), since
 * this is excluded from the regular unit test (`mvn test`) run.
 *
 * NOTE: https://www.powtions.com/effects is a client-rendered SPA, so the
 * raw HTML fetched by Jsoup does not contain a `<table>` with effect data;
 * [EffectsHtmlClient] instead pulls the data out of the JS bundles the page
 * references. See [EffectsHtmlClientTest] for a network-free unit test of
 * that parsing against local copies of the page and its bundles.
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
