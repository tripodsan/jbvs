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

import java.awt.Graphics2D;

public class Circle extends Shape {

    // radius
    double r;

    public Circle(Transformation t, Vector direction, double r) {
        super(t, direction);
        this.r = r;
    }

    public double getRadius() {
        return r;
    }

    public void draw(Graphics2D g) {
        Point c = getGlobalPosition();
        g.drawOval((int) (c.x-r), (int) (c.y-r), (int) (2*r), (int) (2*r));
    }

    public double intersect(Line l) {
        Vector v = l.getGlobalDirection();
        Point c = getGlobalPosition();
        double dx = v.x - c.x;
        double dy = v.y - c.y;
        double d4 = (r + dx*Math.sin(v.a) - dy*Math.cos(v.a))*(r - dx*Math.sin(v.a) + dy*Math.cos(v.a));
        if (d4<0) {
            return Double.MAX_VALUE;
        }
        double b = dx * Math.cos(v.a) + dy * Math.sin(v.a);
        double t1 = Math.sqrt(d4) - b;
        if (t1<0) {
            t1 = Double.MAX_VALUE;
        }
        double t2 = - Math.sqrt(d4) - b;
        if (t2<0) {
            t2 = Double.MAX_VALUE;
        }
        double t = Math.min(t1, t2);
        return t <= l.getLength() ? t : Double.MAX_VALUE;
    }

    public boolean intersects(Circle circle) {
        Point c1 = getGlobalPosition();
        Point c2 = circle.getGlobalPosition();
        double d2 = c1.getDistanceSq(c2);
        double r2 = (r + circle.r) * (r + circle.r);
        return d2 < r2;
    }

    /**
     * local point q
     * @param q
     * @return
     */
    public boolean contains(Point q) {
        return getPosition().getDistanceSq(q) <= r*r;
    }

}
