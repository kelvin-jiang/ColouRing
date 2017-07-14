# ColouRing
Do you remember the days when colour gradient backgrounds were in style? **ColouRing** aims to bring that back! Enjoy an original game board design that employs our own colour-production algorithm to determine the best gradient in this fast-paced, dynamic Android mobile game.

*Every round is unique. Every colour is unique. Every experience is unique.*

> Current Version: 3.0

<a href='https://play.google.com/store/apps/details?id=com.focusstudios.android.colouring'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width=200 /></a>

## How to Play
![Screenshot](/app/src/main/res/drawable-xxhdpi/screenshot1) ![Screenshot](/app/src/main/res/drawable-xxhdpi/screenshot3)

The objective of ColouRing is to form the best possible colour gradient from the inner circle to the outer ring. The center circle will be a predetermined colour, which you do not need to tap. Tap on one of the four sections of each ring to select a colour. The other sections of that ring will be grayed out.

Continue to work your way out of the circle. Keep in mind that the selected path must be connected between rings. If you have chosen an incorrect path, you will not proceed. The path can be deselected to reveal the rings by tapping in each ring again.

If you have chosen the correct path, you will proceed to the next round. Keep your eye on the clock and complete all 10 rounds as fast as you can. Will you be the next ColouRing master?

## Summary of the colour-production algorithm
By treating each colour as a vector in RGB-space, we can determine the gradient between two colours through linear interpolation.

The other colours are determined by choosing points on a plane normal to the vector formed by the gradient colours.

## Credits
Developed by Kelvin Jiang and Patrick Zhang

App icon by Angelina Jin

Music by [Kevin MacLeod](http://incompetech.com)

## License
> Copyright 2017 Focus Studios

> Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

> [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

>Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
