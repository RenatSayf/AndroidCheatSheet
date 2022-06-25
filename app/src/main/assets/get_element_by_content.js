const findNodeByContent = (text, root = document.body) => {
  const treeWalker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);

  const nodeList = [];

  while (treeWalker.nextNode()) {
    const node = treeWalker.currentNode;

    if (node.nodeType === Node.TEXT_NODE && node.textContent.includes(text)) {
      nodeList.push(node.parentNode);
    }
  };

  return nodeList;
}

let span = findNodeByContent('TODO VIewBinding Step 3');

span[0].style.backgroundColor = "red";