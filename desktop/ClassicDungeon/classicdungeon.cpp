#include "classicdungeon.h"
#include "ui_classicdungeon.h"

ClassicDungeon::ClassicDungeon(QWidget *parent) : QMainWindow(parent), ui(new Ui::ClassicDungeon) {
    ui->setupUi(this);
}

ClassicDungeon::~ClassicDungeon() {
    delete ui;
}

