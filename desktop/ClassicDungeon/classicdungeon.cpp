#include "classicdungeon.h"
#include "glwidget.h"
#include "ui_classicdungeon.h"

const qint32 ClassicDungeon::interval = 16;

ClassicDungeon::ClassicDungeon(QWidget *parent): QMainWindow(parent), ui(new Ui::ClassicDungeon) {
    ui->setupUi(this);

    loop = new QTimer(this);
    connect(loop, &QTimer::timeout, ui->surface, qOverload<>(&GLWidget::repaint));
    loop->start(interval);
}

ClassicDungeon::~ClassicDungeon() {
    delete ui;
}
