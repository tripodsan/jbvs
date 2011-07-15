/*
 * Copyright 2011 Tobias Bocanegra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.geom.Point2D;

public class Geom {
    public static final double ROT90 = Math.toRadians(90);
    public static final double ROT180 = Math.toRadians(180);
    public static final double ROT270 = Math.toRadians(270);

    public static Point2D rotate(Point2D p, double a) {
        double x = p.getX() * Math.cos(a) - p.getY() * Math.sin(a);
        double y = p.getX() * Math.sin(a) + p.getY() * Math.cos(a);
        p.setLocation(x, y);
        return p;
    }
}
