#ifndef CLASSICDUNGEON_H
#define CLASSICDUNGEON_H

#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class ClassicDungeon; }
QT_END_NAMESPACE

class ClassicDungeon : public QMainWindow
{
    Q_OBJECT

public:
    ClassicDungeon(QWidget *parent = nullptr);
    ~ClassicDungeon();

private:
    Ui::ClassicDungeon *ui;
};
#endif // CLASSICDUNGEON_H
