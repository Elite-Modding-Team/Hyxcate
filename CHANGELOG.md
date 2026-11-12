# Hyxcate Changelog (1.2.2+)
## 2.0.0
### Update Notes
This is a massive update that overhauls the entire mod. The mod id has been changed with many classes renamed and moved around.
**THIS WILL BREAK ALL NYX AND HYXCATE INTEGRATION FROM EXISTING MODS!**

Datafixers have been utilized to transfer old ids into the new update, but it is still highly recommended up to backup your worlds. 
### Added
- Added API features for celestial events, it should now be easier to make addons that add more events
- Added a new particle system that will be used throughout the mod
- Added more easter eggs!
- Added (back) the Eyezor,  a zombie variant that is faster and can shoot lasers! It can be found in most active events
- Added config option to disable F3 debug info added by Hyxcate
- Added config option to disable easter eggs
- Added a new animation for falling meteors
- Added a new growth animation to the sun when affected by events like Red Giant
- Added the ability to adjust the sun size for all solar events (API)
- Added an icon for the Deep Freeze effect
- Added new vignette screen effects to Hyxcate potion effects
- Added new vignette screen effect to the Red Giant event
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
- Event color transitions are now faster by default
- Tweaked Blood Moon, Blue Moon, Grim Eclipse, and Star Shower sky colors
- Slightly adjusted Alien Kitty textures to be less noisy
- Hostile mobs will now burn under the Red Giant again (another upcoming solar event will still protect hostile mobs from burning)
- Disabled potion particles from Hyxcate potion effects
- Various parts of the event transition times can now be configured
- Event fog color can now be configured rather than using the same color as the sky
- Lightmap brightness is now affected by moon phases
- Optimized a lot of assets which overall reduces the mod file size (courtesy of Demani)
### Fixed
- Fixed meteor block config lists not accepting metadata
- Fixed event notifications and event intro sounds not being client-sided
- Fixed Eyezor AI
- Hopefully fixed Celestial Emblem crafting sound not working under specific circumstances
- Fixed event color transitions not working with the `/hyxcateforce` command
- Fixed event color transitions sometimes not transitioning properly after the event ends
- Fixed event colors immediately reverting back when switching dimensions in the middle of an event
- Fixed night event sky color not actually working at all
- Fixed odd behaviors when the glow effect is applied by Astral Erosion
- Fixed Hyxcate potion effects causing a crash when applied by food
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