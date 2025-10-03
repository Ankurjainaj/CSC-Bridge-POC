#include<bits/stdc++.h>

using namespace std;

struct Node{
    map<int, Node*> links;
    bool flag;

    Node()
    {
        flag=false;
    }
};

class Trie{
    private:
        Node* root;
    public:
        Trie()
        {
            root = new Node();
        }

        void insert(int num)
        {
            Node* node = root;
            for(int i=31;i>=0;i--)
            {
                int bit = (num>>i) & 1;
                if(!node->links[bit])
                {
                    node->links[bit] = new Node();
                }
                node = node->links[bit];
            }
        }

        int getMax(int num)
        {
            Node* node = root;
            int maxNum = 0;
            for(int i=31;i>=0;i--)
            {
                int bit = (num>>i) & 1;
                if(!node->links[1-bit])    //opposite bit exists
                {
                    maxNum = maxNum | (1<<i);   //add into maxNum
                }
                else
                {
                    node = node->links[bit];
                }
            }
            return maxNum;
        }
};

int maxXor(vector<int>& arr1, vector<int>& arr2)
{
    Trie t;
    for(auto& it: arr1)
    {
        t.insert(it);
    }
    int maxXor = 0;
    for(auto& it: arr2)
    {
        maxXor = max(maxXor, t.getMax(it));
    }
    return maxXor;
}

int main()
{
    vector<int> arr1, arr2;
    
}
