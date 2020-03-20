const data = {
  "nodes": [
    {
      "id": "svr",
      "label": "server",
      "x": 5,
      "y": 0,
      "size": 38
    }, {
      "id": "h1",
      "label": "hub",
      "x": 2,
      "y": 2,
      "size": 20
    }, {
      "id": "h2",
      "label": "hub",
      "x": 8,
      "y": 2,
      "size": 20
    }, {
      "id": "n1",
      
      "label": "node",
      "x": 0,
      "y": 4,
      "size": 10
    }, {
      "id": "n2",
      
      "label": "node",
      "x": 2,
      "y": 4,
      "size": 10
    }, {
      "id": "n3",
      
      "label": "node",
      "x": 4,
      "y": 4,
      "size": 10
    }, {
      "id": "n4",
      
      "label": "node",
      "x": 6,
      "y": 4,
      "size": 10
    }, {
      "id": "n5",
      
      "label": "node",
      "x": 8,
      "y": 4,
      "size": 10
    }, {
      "id": "n6",
      
      "label": "node",
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
      "size": 10,
      "color" : "#bcc3cd"
    }, {
      "id": "e1",
      "source": "svr",
      "target": "h2",
      "size": 10,
      "color" : "#bcc3cd"
    }, {
      "id": "e2",
      "source": "h1",
      "target": "n1",
      "size": 7,
      "color" : "#bcc3cd"
    }, {
      "id": "e3",
      "source": "h1",
      "target": "n2",
      "size": 7,
      "color" : "#bcc3cd"
    }, {
      "id": "e4",
      "source": "h1",
      "target": "n3",
      "size": 7,
      "color" : "#bcc3cd"
    }, {
      "id": "e5",
      "source": "h2",
      "target": "n4",
      "size": 7,
      "color" : "#bcc3cd"
    }, {
      "id": "e6",
      "source": "h2",
      "target": "n5",
      "size": 7,
      "color" : "#bcc3cd"
    }, {
      "id": "e7",
      "source": "h2",
      "target": "n6",
      "size": 7,
      "color" : "#bcc3cd"
    }
  ]
};


var cfg = {
    defaultNbodeType: 'def',
    defaultEdgeType: 'def',
    defaultLabelColor: '#000',
    defaultEdgeColor: '#bcc3cd',
    defaultNodeColor: '#f1e28b',
    defaultLabelSize: 14,
    edgeColor: 'source',
    defaultNodeBorderColor: '#000',
    defaultNodeHoverColor: '#000',
    defaultLabelHoverColor: '#000',
    edgeHoverColor: 'edge',
    edgeHoverSizeRatio: 2
}

var s = new sigma({
  graph: data,
  container: 'sigma-container',
  settings: {
	defaultNodeColor: '#f1e28b', // yellow
	defaultEdgeColor: '#bcc3cd', // blue gray
    defaultLabelColor: '#ffffff00',
    defaultLabelHoverColor: '#f1e28b',
    defaultHoverLabelBGColor: '#ffffff00',
    edgeColor: 'source',
    defaultLabelSize: 20,
    font: 'Raleway',
    maxNodeSize: 50,
      sideMargin: 2
  }
});

