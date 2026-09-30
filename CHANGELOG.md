# Hyxcate Changelog (1.2.2+)
## 2.0.0
### Update Notes
This is a massive update that overhauls the entire mod. The mod id has been changed with many classes renamed and moved around.
**THIS WILL BREAK ALL NYX AND HYXCATE INTEGRATION FROM EXISTING MODS!**
### Added
- Added API features for celestial events, it should now be easier to make addons that add more events
- Added a new particle system that will be used throughout the mod
- Added more easter eggs!
- Added (back) the Eyezor,  a zombie variant that is faster and can shoot lasers! It can be found in most active events
- Added config option to disable F3 debug info added by Hyxcate
- Added a new animation for falling meteors
- Added fr_fr.lang (courtesy of Demani)
- Added ru_ru.lang (courtesy of GitNell)
- Added uk_ua.lang (courtesy of GitNell)
### Changed
- Changed mod id: `nyx` -> `hyxcate`
- Updated datafixers to apply for older versions of Hyxcate before the mod id change
- Moved classes around, cleaned up messy code, and renamed classes to make more sense
- Eyezors encountered in Star Showers now have a different texture variant
- Eyezors will now fire lasers faster the more lower their health is
- Nerfed Stunlock trait (Tinkers' Construct integration)
- F3 debug info is now a little more descriptive about the type of celestial event that is active
- Celestial Warhammer will now apply Astral Erosion for 8 seconds when hitting a mob
- Updated Celestial Warhammer particles, also added particles when a mob is hit with it
- Updated all meteor type particles, there are different particle colors depending on the variant of meteor
- Updated Cyber Crystal particles
### Fixed
- Fixed meteor block config lists not accepting metadata
- Fixed event notifications and event intro sounds not being client-sided
- Fixed Eyezor AI
### Removed
- Removed beam sword swing and hit sounds, these were relocated to another project
---
## 1.2.4
### Changed
- Meteor types and meteor sizes can now be configured
### Fixed
- Special spawns should now be fixed in most events (Blood Moon special spawns are not fixed yet)
---
## 1.2.3
### Changed
- Updated commands, you can now additionally use `/hyx` or `/hyxcate` for both commands (e.g. `/hyxforce` or `/hyxcatemeteor`), see the wiki for more info about commands
### Fixed
- Fixed lightmaps not working properly
---
## 1.2.2
### Fixed
- Fixed a crash caused by the enchantment attributes event not having a proper check
- Fixed a crash caused by special event entity spawns