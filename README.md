

# Follow code style
  https://github.com/CrownStack/crownstack-playbook/wiki/android-Playbook

# Git guideline
  https://github.com/CrownStack/crownstack-playbook/wiki/Git-Usage-Guidelines

# Fastlane guideline
  * Fastlane setup
    https://docs.fastlane.tools/getting-started/android/setup/

  * Install plugins
    fastlane add_plugin get_version_name

  * Install `imagemagick`.

  * Create build and upload on developer hockey app
    fastlane developers

  * Create build and upload on production hockey app
    fastlane production

# Download build
  Go to HockeyApp Dashboard. Select app and you can check the build by version.
  Download build.

## Flavor

  Define the productFlavour in app/build.gradle. To upload the apk on hockeyApp define lane in fastFile for different flavour.
  There are two flavour in this project. Android build system pick the BASE_URL(other creadential) on the basis of buildFalvour.

  ### Dev
  * Go in git repository directory.
  * Run ./build.sh to upload a build with development BASE_URL to create a new tag and push on remote. It will also upload apk on developer hockey app.


  ### Production
  * Go in git repository directory.
  * Run ./production_build.sh to upload a build with production BASE_URL to create a new tag and push on remote. It will also upload apk on production hockey app.

