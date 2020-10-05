# VideoEnhancementPlus

a xposed module that let xperia mobile video enhancement feature(a.k.a X-Reality) work for every application

![pic](compare.png)
<div align="center">before/after</div>

## usage

1. install edxposed and this module
2. active the module
3. if you are using whitelist mode in xposed, add `com.sonymobile.swiqisystemservice` and `com.sonymobile.displaybooster` to your whitelist
4. install [VideoEnhancementPlusExtra](https://github.com/rhjdvsgsgks/VideoEnhancementPlusExtra) to make able to control CABC (optional)

## about opensource

for convenience, im using a part code(`KotlinXposedHelper.kt` and `Log.kt`) from [BiliRoaming](https://github.com/yujincheng08/BiliRoaming), if you mind this plesse tell me, i will rewrite this part of code by myself
