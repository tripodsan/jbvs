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
package geom;

public class Point {

    // x - coordinate of point
    public final double x;

    // y - coordinate of point
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public Point rotate(double a) {
        double xp = x * Math.cos(a) - y * Math.sin(a);
        double yp = x * Math.sin(a) + y * Math.cos(a);
        return new Point(xp, yp);
    }

    public Point rotate(double a, Point o) {
        double xp = (x - o.x) * Math.cos(a) - (y - o.y) * Math.sin(a);
        double yp = (x - o.x) * Math.sin(a) + (y - o.y) * Math.cos(a);
        return new Point(xp + o.x, yp + o.y);
    }

    public Point translate(Point p, boolean reverse) {
        if (reverse) {
            return new Point(x - p.x, y - p.y);
        } else {
            return new Point(x + p.x, y + p.y);
        }
    }

    public double getAngle(Point p) {
        return Math.atan2(p.y - y, p.x - x);
    }

    public double getDistance(Point p) {
        return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y));
    }

    public double getDistanceSq(Point p) {
        return (p.x-x)*(p.x-x) + (p.y-y)*(p.y-y);
    }

}
