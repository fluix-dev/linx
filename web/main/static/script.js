const data = {
  "nodes": [
    {
      "id": "svr",
      "label": "The Server",
      "x": 5,
      "y": 0,
      "size": 25
    }, {
      "id": "h1",
      "label": "Hub",
      "x": 2,
      "y": 2,
      "size": 15
    }, {
      "id": "h2",
      "label": "Hub",
      "x": 8,
      "y": 2,
      "size": 15
    }, {
      "id": "n1",
      "label": "Node",
      "x": 0,
      "y": 4,
      "size": 10
    }, {
      "id": "n2",
      "label": "Node",
      "x": 2,
      "y": 4,
      "size": 10
    }, {
      "id": "n3",
      "label": "Node",
      "x": 4,
      "y": 4,
      "size": 10
    }, {
      "id": "n4",
      "label": "Node",
      "x": 6,
      "y": 4,
      "size": 10
    }, {
      "id": "n5",
      "label": "Node",
      "x": 8,
      "y": 4,
      "size": 10
    }, {
      "id": "n6",
      "label": "Node",
      "x": 10,
      "y": 4,
      "size": 10
    }
  ],
  "edges": [
    {
      "id": "e0",
      "source": "svr",
      "target": "h1",
      "color" : "#FF0000"
    }, {
      "id": "e1",
      "source": "svr",
      "target": "h2"
    }, {
      "id": "e2",
      "source": "h1",
      "target": "n1"
    }, {
      "id": "e3",
      "source": "h1",
      "target": "n2"
    }, {
      "id": "e4",
      "source": "h1",
      "target": "n3"
    }, {
      "id": "e5",
      "source": "h2",
      "target": "n4"
    }, {
      "id": "e6",
      "source": "h2",
      "target": "n5"
    }, {
      "id": "e7",
      "source": "h2",
      "target": "n6"
    }
  ]
};


var cfg = {
    defaultNodeType: 'def',
    defaultEdgeType: 'def',
    defaultLabelColor: '#000',
    defaultEdgeColor: '#bcc3cd',
    defaultNodeColor: '#f1e28b',
    defaultLabelSize: 14,
    edgeColor: 'source',
    defaultNodeBorderColor: '#000',
    defaultNodeHoverColor: '#000',
    edgeHoverColor: 'edge',
    edgeHoverSizeRatio: 1
}

var s = new sigma({
  graph: data,
  container: 'container',
  settings: {
	defaultNodeColor: '#f1e28b', // yellow
	defaultEdgeColor: '#bcc3cd', // blue gray
    edgeColor: 'source'
    // animationsTime: 1000	
  }
});

