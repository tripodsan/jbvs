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

import java.util.HashSet;
import java.util.Iterator;

public class Transformation {

    public static final Transformation IDENTITY = new Transformation(null, new Vector(0,0,0));

    protected final Transformation parent;

    private Vector direction;

    private HashSet listeners = new HashSet();

    public Transformation(Vector direction) {
        this.parent = IDENTITY;
        this.direction = direction;
    }

    public Transformation(Transformation parent, Vector direction) {
        this.parent = parent == null ? IDENTITY : parent;
        this.direction = direction;
    }

    public Vector transform(Vector v, boolean reverse) {
        if (reverse) {
            if (parent != null) {
                v = parent.transform(v, true);
            }
            v = v.translate(direction.p, true);
            v = v.rotate(-direction.a);
        } else {
            v = v.rotate(direction.a);
            v = v.translate(direction.p, false);
            if (parent != null) {
                v = parent.transform(v, false);
            }
        }
        return v;
    }

    public Point transform(Point p, boolean reverse) {
        if (reverse) {
            if (parent != null) {
                p = parent.transform(p, true);
            }
            p = p.translate(direction.p, true);
            p = p.rotate(-direction.a);
        } else {
            p = p.rotate(direction.a);
            p = p.translate(direction.p, false);
            if (parent != null) {
                p = parent.transform(p, false);
            }
        }
        return p;
    }

    public double transform(double a, boolean reverse) {
        if (reverse) {
            if (parent != null) {
                a = parent.transform(a, true);
            }
            a -= direction.a;
        } else {
            a += direction.a;
            if (parent != null) {
                a = parent.transform(a, false);
            }
        }
        return a;
    }

    public void registerListener(TransformationListener l) {
        listeners.add(l);
    }

    public void unregisterListener(TransformationListener l) {
        listeners.remove(l);
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
        notifyListeners();
    }

    private void notifyListeners() {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            ((TransformationListener) iter.next()).transformationChanged(this);
        }
    }
}
