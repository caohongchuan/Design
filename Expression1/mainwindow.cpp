#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <string>
#include <QMessageBox>


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

}

MainWindow::~MainWindow(){
    delete ui;
}


void MainWindow::on_input1_clicked(){
    string expr;
    expr=ui->input_pre->text().toStdString();
    exc.pre_Make(expr);
    ui->show_value->clear();

    ui->show_in->clear();
    ui->show_pre->clear();
    ui->show_post->clear();
    ui->val->clear();
    ui->value_of_val->clear();
    ui->all_of_val->clear();
    ma.clear();

    if(exc.input_success==false){
        QMessageBox::warning(NULL, "warning", "The input is invaild.",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        exc.input_success=true;
        return;
    }
}


void MainWindow::on_input2_clicked(){
    string expr;
    expr=ui->input_in->text().toStdString();
    exc.in_Make(expr);

    ui->show_value->clear();
    ui->show_in->clear();
    ui->show_pre->clear();
    ui->show_post->clear();
    ui->val->clear();
    ui->value_of_val->clear();
    ui->all_of_val->clear();
    ma.clear();

    if(exc.input_success==false){
        QMessageBox::warning(NULL, "warning", "The input is invaild.",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        exc.input_success=true;
        return;
    }
}


void MainWindow::on_actionValue_triggered(){
    if(exc.empty()){
        QMessageBox::warning(NULL, "warning", "Input the expression first!",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    //assign
    for(auto xx:ma){
       exc.Assign(xx.first,xx.second);
    }


    double result=exc.Value();

    if(exc.have_variable==true){
        exc.have_variable=false;
        QMessageBox::warning(NULL, "warning", "There are variables having no value.",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    string output;
    stringstream ss;
    ss<<result;
    ss>>output;
    ui->show_value->setText(QString::fromStdString(output));
}


void MainWindow::on_actionPrefix_triggered(){
    if(exc.empty()){
        QMessageBox::warning(NULL, "warning", "Input the expression first!",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    exc.prefix_output();
    ui->show_pre->setText(QString::fromStdString(exc.show_string()));
}


void MainWindow::on_actionInfix_triggered(){
    if(exc.empty()){
        QMessageBox::warning(NULL, "warning", "Input the expression first!",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    exc.infix_output();
    ui->show_in->setText(QString::fromStdString(exc.show_string()));

}

void MainWindow::on_actionPostfix_triggered(){
    if(exc.empty()){
        QMessageBox::warning(NULL, "warning", "Input the expression first!",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    exc.suffix_output();
    ui->show_post->setText(QString::fromStdString(exc.show_string()));
}

void MainWindow::on_actionSimplify_T_triggered(){
    if(exc.empty()){
        QMessageBox::warning(NULL, "warning", "Input the expression first!",QMessageBox::Yes | QMessageBox::No, QMessageBox::Yes);
        return;
    }

    exc.mergeconst();

    exc.infix_output();
    ui->show_in->setText(QString::fromStdString(exc.show_string()));

}

void MainWindow::on_pushButton_clicked(){
    string x=ui->val->text().toStdString();
    string num=ui->value_of_val->text().toStdString();
    double x_num;
    stringstream ss;
    ss<<num;
    ss>>x_num;


    ma[x]=x_num;

    string output_var;

    for(auto xx:ma){
        string record;
        output_var+=xx.first;
        output_var+=" = ";

        stringstream sss;
        sss<<xx.second;
        sss>>record;
        output_var+=record;
        output_var+="\n";
    }

    ui->all_of_val->setText(QString::fromStdString(output_var));

}
