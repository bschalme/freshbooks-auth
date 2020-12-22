# FreshBooks Auth

This is an example that shows how to authenticate with the [New FreshBooks](https://www.freshbooks.com/api/start) 
using the OAuth 2.0 Authorization Code grant and [Micronaut](https://micronaut.io)'s 
[OAuth 2.0 library](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html#oauth).

To get this example working, you'll need to set up an account at [FreshBooks](https://www.freshbooks.com), 
and then use their [Developer Portal](https://my.freshbooks.com/#/developer) to define an Application. 
They have a free tier.

Back here, copy `sentenv-sample` to `setenv`, and chmod +x it. Do the same for `envfile-sample`, copying it to 
`envfile`.

Now grab the Client ID and Client Secret from the FreshBooks Application you just created, and use them to 
populate `OAUTH_CLIENT_ID` and `OAUTH_CLIENT_SECRET` in setenv and envfile.

Make up a 256-bit JWT key and use it to populate `JWT_SECRET` in sentenv and envfile. I used 
[RandomKeygen](https://randomkeygen.com) to generate my 256-bit key.

Make sure you're using Java 11 or greater.

Dot-execute setenv  to set those three environment variables:

* `. ./setenv`

Now go `./gradlew run`. Browse to https://localhost. Approve any security warnings
as this generates a self-signed TLS certificate. Click on the Enter link. Away you go!