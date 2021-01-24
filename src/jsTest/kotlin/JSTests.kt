class JSTests {
    suspend fun basicTestJS() {
        println("OK")
        IntegrationTests().basicTestWithToken(Config("", ""))
    }
}