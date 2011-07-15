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

abstract public class Shape extends Transformation implements TransformationListener {

    /**
     * the global direction, i.e. after transformed
     */
    private Vector globalDirection;

    public Shape(Transformation transformation, Vector direction) {
        super(transformation, direction);
        if (transformation != null) {
            transformation.registerListener(this);
        }
        setDirection(direction);
    }

    public void transformationChanged(Transformation t) {
        globalDirection = parent.transform(getDirection(), false);
        super.setDirection(getDirection());
    }

    public Vector getGlobalDirection() {
        return globalDirection;
    }

    public Point getPosition() {
        return getDirection().p;
    }

    public double getOrientation() {
        return getDirection().a;
    }

    public void setGlobalDirection(Vector v) {
        setDirection(parent.transform(v, true));
    }

    public void setGlobalPosition(Point p) {
        setGlobalDirection(new Vector(p, globalDirection.a));
    }

    public void setGlobalOrientation(double a) {
        setGlobalDirection(new Vector(globalDirection.p, a));
    }

    public Point getGlobalPosition() {
        return globalDirection.p;
    }

    public double getGlobalOrientation() {
        return globalDirection.a;
    }

    public void setDirection(Vector direction) {
        super.setDirection(direction);
        globalDirection = parent.transform(direction, false);
    }

    public Transformation getTransformation() {
        return parent;
    }
}
