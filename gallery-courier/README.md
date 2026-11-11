# Zork Gallery Courier — Android prototype v1.0.1

## Purpose
Choose multiple images from Android's system file/photo picker, then use the system Share menu to send them to an installed destination such as Google Drive or Gmail.

## What it does
- Opens Android's system document picker filtered to images.
- Allows multi-selection where the installed picker supports it.
- Shares selected image URIs with temporary read permission through Android's share chooser.
- Does not require broad photo-library permission, upload credentials, or network access of its own.
- Does not alter or delete originals.

## Important limitations
- This is a source-code prototype, **not a built APK**; it has not been compiled or tested on a phone.
- Android Studio with a compatible Android SDK/Gradle JDK is needed to build and test it. The project pins Android Gradle Plugin 8.7.3 and Kotlin 2.0.21. It does not include a Gradle wrapper or a compiled APK.
- The user must select a destination in the share menu and confirm any destination-specific upload or send.
- Sharing to Google Drive or Gmail does not automatically post images into a ChatGPT conversation. Zork must subsequently retrieve accessible content through an appropriate connected account or the user can upload directly to the chat.
- Very large batches may exceed limits of the destination app. Test with 2–3 photos first.
- The prototype doesn't scan the whole gallery, watch folders, auto-send, or connect to Chip Zork.

## Build
1. Open this folder as a project in Android Studio.
2. Let Android Studio install required SDK packages and sync Gradle.
3. Build > Build APK(s), or run on a test device.
4. Install only an APK you built or obtained from a trusted developer; review permissions.

## Planned v2
Optional named destination (private Drive inbox), delivery confirmation, transfer log, and Chip Zork desktop ingestion. No automatic uploads without explicit user opt-in.
