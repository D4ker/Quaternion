import static java.lang.Math.*;

public final class Quaternion {

    // Компоненты Кватерниона
    private double w, x, y, z;

    // Построение Кватерниона через угол поворота и ось вращения
    public Quaternion(double angle, double x, double y, double z) {
        final double halfAngle = angle / 2;
        w = cos(halfAngle);
        if (x != 0 || y != 0 || z != 0) {
            final double newSin = sin(halfAngle);
            final double vectorLength = sqrt(x * x + y * y + z * z);
            this.x = x / vectorLength * newSin;
            this.y = y / vectorLength * newSin;
            this.z = z / vectorLength * newSin;
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    // Построение "нулевого" Кватерниона
    public Quaternion() {
        w = 0;
        x = 0;
        y = 0;
        z = 0;
    }

    // Построение Кватерниона через компоненты
    public Quaternion create(double w, double x, double y, double z) {
        final Quaternion newQ = new Quaternion();
        newQ.w = w;
        newQ.x = x;
        newQ.y = y;
        newQ.z = z;
        return newQ;
    }

    // Построение Кватерниона на основе уже имеющегося Кватерниона
    public Quaternion(Quaternion q) {
        create(q.w, q.x, q.y, q.z);
    }

    // Описание методов доступа к компонентам (полям) Кватерниона
    public double[] get() {
        return new double[]{w, x, y, z};
    }

    public double getW() {
        return w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Возвращает скалярную часть Кватерниона
    public double getScalar() {
        return w;
    }

    // Возвращает компоненты векторной части Кватерниона в виде массива
    public double[] getVector() {
        return new double[]{x, y, z};
    }

    // Возвращает векторную часть Кватерниона в виде строки
    public String getVectorString() {
        return String.format("%+fi%+fj%+fk", x, y, z);
    }

    // Описание методов изменения компонент (полей) Кватерниона
    public void setAngle(double angle) {
        w = cos(angle / 2);
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    // Возвращает сопряженный Кватернион
    public Quaternion conjugate() {
        return create(w, -x, -y, -z);
    }

    // Возвращает модуль Кватерниона
    public double module() {
        return sqrt(w * w + x * x + y * y + z * z);
    }

    // Деление (обращение) Кватерниона
    public Quaternion divide() {
        final double module = module();
        if (module == 0) return this;
        final double squareModule = module * module;
        return create(w / squareModule, -x / squareModule, -y / squareModule, -z / squareModule);
    }

    // Перемножение Кватернионов
    public Quaternion multiply(Quaternion other) {
        final double newW = w * other.w - x * other.x - y * other.y - z * other.z;
        final double newX = w * other.x + x * other.w + y * other.z - z * other.y;
        final double newY = w * other.y - x * other.z + y * other.w + z * other.x;
        final double newZ = w * other.z + x * other.y - y * other.x + z * other.w;
        return create(newW, newX, newY, newZ);
    }

    // Умножение Кватерниона на скаляр
    public Quaternion multiply(double value) {
        return create(w * value, x * value, y * value, z * value);
    }

    // Сложение Кватернионов
    public Quaternion add(Quaternion other) {
        return create(w + other.w, x + other.x, y + other.y, z + other.z);
    }

    // Вычитание Кватернионов
    public Quaternion deduct(Quaternion other) {
        return create(w - other.w, x - other.x, y - other.y, z - other.z);
    }

    // Нормирование Кватерниона
    public Quaternion normalize() {
        final double length = module();
        if (length == 0) return this;
        return create(w / length, x / length, y / length, z / length);
    }

    // Сравнение Кватернионов
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof Quaternion) {
            Quaternion other = (Quaternion) object;
            return w == other.w && x == other.x && y == other.y && z == other.z;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(w);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    // Строковое представление Кватерниона
    @Override
    public String toString() {
        return String.format("%f%+fi%+fj%+fk", w, x, y, z);
    }
}