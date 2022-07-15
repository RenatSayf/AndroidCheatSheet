const findNodeByContent = (text, color  = "aliceblue", root = document.body) => {
  const treeWalker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);

  const nodeList = [];

  while (treeWalker.nextNode()) {
    const node = treeWalker.currentNode;

    if (node.nodeType === Node.TEXT_NODE && node.textContent.includes(text)) {
      nodeList.push(node.parentNode);
    }
  };

  nodeList.forEach(function(item) { item.parentElement.style.backgroundColor = color })

  return nodeList;
}

let span = findNodeByContent('Hint VIewBinding Step2');

span[0].style.backgroundColor = "red";

span.forEach(function(item) {
    item.style.backgroundColor = "red"
'red'});