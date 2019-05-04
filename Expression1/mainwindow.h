#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "expr.h"
#include <map>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    void get_In_Make();
    void get_Pre_Make();

    void show_Pre();
    void show_In();
    void show_Post();

private slots:
    void on_actionValue_triggered();



    void on_input1_clicked();

    void on_input2_clicked();

    void on_actionPrefix_triggered();

    void on_actionInfix_triggered();

    void on_actionPostfix_triggered();

    void on_actionSimplify_T_triggered();

    void on_pushButton_clicked();

private:
    Ui::MainWindow *ui;
    Except<double> exc;
    map<string,double> ma;
};

#endif // MAINWINDOW_H
