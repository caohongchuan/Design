#ifndef LINKEDBINARYTREE_H
#define LINKEDBINARYTREE_H


// linked binary tree using nodes of type binaryTreeNode
// derives from the abstract class binaryTree




#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <algorithm>
#include <map>
#include "myexceptions.h"
#include <sstream>
#include <cmath>

using namespace std;



//the Treenode struct
template <class T>
class binaryTreeNode {
    public:
        T element;
        binaryTreeNode<T> *leftChild,   // left subtree
                        *rightChild;  // right subtree

        binaryTreeNode() {
            leftChild = rightChild = NULL;
        }
        binaryTreeNode(const T& theElement):element(theElement) {
            leftChild = rightChild = NULL;
        }
        binaryTreeNode(const T& theElement,binaryTreeNode *theLeftChild,binaryTreeNode *theRightChild):element(theElement) {
            leftChild = theLeftChild;
            rightChild = theRightChild;
        }
};

static string res1;

template<class E>
class linkedBinaryTree {
public:
    linkedBinaryTree() {
        root = NULL;
        treeSize = 0;
    }
    //can't define destructure
    bool empty() const { return treeSize == 0;  }
    int size() const {return treeSize; }
    E* rootElement() const;

    int height() const { return height(root); }



    //the main function
    void makeTree(const E& element, linkedBinaryTree<E>&, linkedBinaryTree<E>&);



    //change assign
    void preassign(string x,E c_num){

        int index=100;
        E result=0;
        for(size_t i=0;i<x.size();i++){
            result*=index;
            result=result+int(x[i])-23;
        }
        presearch(root,result+maxxx,c_num);
    }

    void presearch(binaryTreeNode<E> *t,E num,E c_num){
        if (t != NULL) {
            if(t->element==num){
                t->element=c_num;
            }
            presearch(t->leftChild,num,c_num);
            presearch(t->rightChild,num,c_num);
        }
    }

    vector<E> get_postring(){
        postsearch(root);
        return postring;
    }
    void postsearch(binaryTreeNode<E> *t){
        if(t!=NULL){
            postsearch(t->leftChild);
            postsearch(t->rightChild);
            postring.push_back(t->element);
        }
    }

    vector<E> get_prestring(){
        presearch_get(root);
        return prestring;
    }
    void presearch_get(binaryTreeNode<E>* t){
        if(t!=NULL){
            prestring.push_back(t->element);
            presearch_get(t->leftChild);
            presearch_get(t->rightChild);
        }
    }

    void mergeconst_tree(){
        simple(root,NULL);
    }

    bool unite(binaryTreeNode<E>*cur ,binaryTreeNode<E>*father,binaryTreeNode<E>* mer,binaryTreeNode<E>* operat){
        int op1=cur->element-maxxx;
        int op2=father->element-maxxx;
        bool same=false;
        if((op1==43||op1==45)&&(op2==43||op2==45)) same=true;
        if((op1==42||op1==47)&&(op2==42||op2==47)) same=true;
        if((op1==44)&&(op2==44)) same=true;

        if(!same) return false;
        else{
            if(cur->leftChild->element<maxxx){
                int op=operat->element-maxxx;
                if(op==43) cur->leftChild->element+=mer->element;
                else if(op==45) cur->leftChild->element-=mer->element;
                else if(op==42) cur->leftChild->element*=mer->element;
                else if(op==47) cur->leftChild->element/=mer->element;
                else if(op==44) cur->leftChild->element=pow(cur->leftChild->element,mer->element);

                return true;
            }else if(cur->rightChild->element<maxxx){
                int op=operat->element-maxxx;
                if(op==43) cur->rightChild->element+=mer->element;
                else if(op==45) cur->rightChild->element-=mer->element;
                else if(op==42) cur->rightChild->element*=mer->element;
                else if(op==47) cur->rightChild->element/=mer->element;
                else if(op==44) cur->rightChild->element=pow(cur->rightChild->element,mer->element);

                return true;
            }else{
                bool left=unite(cur->leftChild,cur,mer,operat);
                bool right=unite(cur->rightChild,cur,mer,operat);
                if(left||right) return true;
            }

        }
    }

    void simple(binaryTreeNode<E>*t,binaryTreeNode<E>* father){

        if(t->leftChild!=NULL&&t->rightChild!=NULL){
            simple(t->leftChild,t);
            simple(t->rightChild,t);
            //unite
            if(t->leftChild->element>=maxxx&&t->rightChild->element>=maxxx) return ;

            if(t->leftChild->element<maxxx&&t->rightChild->element>=maxxx){
                bool search=unite(t->rightChild,t,t->leftChild,t);
                if(search){
                    if(father==nullptr) {
                        root=t->rightChild;
                    }else{
                        if(father->leftChild==t){
                            father->leftChild=t->rightChild;
                        }else if(father->rightChild==t){
                            father->rightChild=t->rightChild;
                        }
                    }

                    delete t->leftChild;
                    delete t;
                }
            }else if(t->rightChild->element<maxxx&&t->leftChild->element>=maxxx){
                bool search=unite(t->leftChild,t,t->rightChild,t);
                if(search){
                    if(father==nullptr){
                        root=t->leftChild;
                    }else{
                        if(father->leftChild==t){
                            father->leftChild=t->leftChild;
                        }else if(father->rightChild==t){
                            father->rightChild=t->leftChild;
                        }
                    }

                    delete t->rightChild;
                    delete t;
                }
            }else if(t->leftChild->element<maxxx&&t->rightChild->element<maxxx){
                int op=t->element-maxxx;
                if(op==43) t->element=t->leftChild->element+t->rightChild->element;
                else if(op==45) t->element=t->leftChild->element-t->rightChild->element;
                else if(op==42) t->element=t->leftChild->element*t->rightChild->element;
                else if(op==47) t->element=t->leftChild->element/t->rightChild->element;
                else if(op==44) t->element=pow(t->leftChild->element,t->rightChild->element);

                delete t->leftChild;
                delete t->rightChild;

                t->leftChild=nullptr;
                t->rightChild=nullptr;
            }
        }
    }





    void preOrder1(void(*theVisit)(binaryTreeNode<E>*)) {
        visit = theVisit;
        preOrder(root);
    }
    void inOrder1(void(*theVisit)(binaryTreeNode<E>*)) {
        visit = theVisit;
        inOrder(root);
    }
    void postOrder1(void(*theVisit)(binaryTreeNode<E>*)) {
        visit = theVisit;
        postOrder(root);
    }
    void levelOrder(void(*)(binaryTreeNode<E>*));


    //output
    void preOrderOutput() {
        res1.clear();
        this->preOrder1(output1);


    }
    void inOrderOutput() {
        res1.clear();
        this->inOrder1(output1);


    }
    void postOrderOutput() {
        res1.clear();
        this->postOrder1(output1);


    }
    void levelOrderOutput() {
        res1.clear();
        this->levelOrder(output1);

    }

    string result(){
        return res1;
    }

    //destructure
    void erase() {
        this->postOrder(this->dispose);
        root = NULL;
        treeSize = 0;
    }

protected:

    static const long long maxxx=1e9;
                         //assign
    vector<E> postring;
    vector<E> prestring;

    binaryTreeNode<E> *root;                // pointer to root
    int treeSize;                           // number of nodes in tree
    static void (*visit)(binaryTreeNode<E>*);      // visit function
    int count;         // used to count nodes in a subtree

    bool comp(E,E);
    void preOrder(binaryTreeNode<E> *t);
    void inOrder(binaryTreeNode<E> *t);
    void postOrder(binaryTreeNode<E> *t);


    void dispose(binaryTreeNode<E> *t) {
        delete t;
    }

    static void output1(binaryTreeNode<E> *t){

        if(t->element>=maxxx){
            int curr=int(t->element-maxxx);

            if(curr==43) res1+="+ ";
            else if(curr==45) res1+="- ";
            else if(curr==47) res1+="/ ";
            else if(curr==42) res1+="* ";
            else if(curr==44) res1+="^ ";
            else{
                string x;
                int index=0;
                while(curr!=0){
                    x[index++]=char((curr%100)+23);
                    curr/=100;
                }


                for(int i=index-1;i>=0;i--) res1+=x[i];
                res1+=" ";

            }
        }else{
            string res;
            stringstream ss;
            ss << t->element;
            ss >> res;//或者 res = ss.str();
            res1+=res;
            res1+=" ";
        }
    }

    // void vector_get(binaryTreeNode<E> *t){
    //     string_exp.push_back(t->element);
    // }

    void addToCount(binaryTreeNode<E> *t) {count++;}

    int height(binaryTreeNode<E> *t);

    void countNodes(binaryTreeNode<E> *t) {
        visit = this->addToCount;
        count = 0;
        preOrder(t);
    }


};
// the following should work but gives an internal compiler error
// template <class E> void (*linkedBinaryTree<E>::visit)(binaryTreeNode<E>*);
// so the explicit declarations that follow are used for our purpose instead

template<class T>
void (*linkedBinaryTree<T>::visit)(binaryTreeNode<T>*);
// void (*linkedBinaryTree<booster>::visit)(binaryTreeNode<booster>*);
// void (*linkedBinaryTree<pair<int,int> >::visit)(binaryTreeNode<pair<int,int> >*);
// void (*linkedBinaryTree<pair<const int,char> >::visit)(binaryTreeNode<pair<const int,char> >*);
//void (*linkedBinaryTree<pair<const int,int> >::visit)(binaryTreeNode<pair<const int,int> >*);

template<class E>
E* linkedBinaryTree<E>::rootElement() const {
    // Return NULL if no root. Otherwise, return pointer to root element.
    if (treeSize == 0)
        return NULL;  // no root
    else
        return &root->element;
}

template<class E>
void linkedBinaryTree<E>::makeTree(const E& element,
                                   linkedBinaryTree<E>& left, linkedBinaryTree<E>& right) {
    // Combine left, right, and element to make new tree.
// left, right, and this must be different trees.
    // create combined tree
    root = new binaryTreeNode<E> (element, left.root, right.root);
    treeSize = left.treeSize + right.treeSize + 1;

    // deny access from trees left and right
    left.root = right.root = NULL;
    left.treeSize = right.treeSize = 0;
}




template<class E>
void linkedBinaryTree<E>::preOrder(binaryTreeNode<E> *t) {
    // Preorder traversal.
    if (t != NULL) {
        linkedBinaryTree<E>::visit(t);
        preOrder(t->leftChild);
        preOrder(t->rightChild);
    }
}

template<typename E>
bool linkedBinaryTree<E>::comp(E xx,E yy){
    if(yy<maxxx) return false;

    xx=xx-maxxx;
    yy=yy-maxxx;
    if(yy==43||yy==45){
        if(xx==42||xx==47||xx==44) return true;
        else return false;
    }
    else return false;
}



template<class E>
void linkedBinaryTree<E>::inOrder(binaryTreeNode<E> *t) {
    // Inorder traversal.
    if (t != NULL) {
        if(t->leftChild!=NULL){
            bool compare=comp(t->element,t->leftChild->element);
            if(compare) res1+="(";
            inOrder(t->leftChild);
            if(compare) res1+=")";
        }

        linkedBinaryTree<E>::visit(t);

        if(t->rightChild!=NULL){
            bool compare=comp(t->element,t->rightChild->element);
            if(compare) res1+="(";
            inOrder(t->rightChild);
            if(compare) res1+=")";
        }

    }
}

template<class E>
void linkedBinaryTree<E>::postOrder(binaryTreeNode<E> *t) {
    // Postorder traversal.
    if (t != NULL) {
        postOrder(t->leftChild);
        postOrder(t->rightChild);
        linkedBinaryTree<E>::visit(t);
    }
}

template <class E>
void linkedBinaryTree<E>::levelOrder(void(*theVisit)(binaryTreeNode<E> *)) {
    // Level-order traversal.
    queue<binaryTreeNode<E>*> q;
    binaryTreeNode<E> *t = root;
    while (t != NULL) {
        theVisit(t);  // visit t

        // put t's children on queue
        if (t->leftChild != NULL)
            q.push(t->leftChild);
        if (t->rightChild != NULL)
            q.push(t->rightChild);

        // get next node to visit
        try {
            t = q.front();
        } catch (queueEmpty) {
            return;
        }
        q.pop();
    }
}

template <class E>
int linkedBinaryTree<E>::height(binaryTreeNode<E> *t) {
    // Return height of tree rooted at *t.
    if (t == NULL)
        return 0;                    // empty tree
    int hl = height(t->leftChild);  // height of left
    int hr = height(t->rightChild); // height of right
    if (hl > hr)
        return ++hl;
    else
        return ++hr;
}



#endif // LINKEDBINARYTREE_H
