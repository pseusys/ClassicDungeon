#ifndef GLWIDGET_H
#define GLWIDGET_H

#include <QOpenGLWidget>

class GLWidget: public QOpenGLWidget {
    Q_OBJECT

public:
    explicit GLWidget(QWidget *parent = nullptr);

protected:
    void paintEvent(QPaintEvent*) override;
};

#endif // GLWIDGET_H
