#include <QTimer>
#include <QDebug>

#include "glwidget.h"
// #include "../../shared/build/??"

GLWidget::GLWidget(QWidget *parent): QOpenGLWidget(parent) {}

void GLWidget::paintEvent(QPaintEvent*) {
    qDebug() << "update";
}
