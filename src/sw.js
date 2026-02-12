const CACHE_NAME = 'timer-helper-v1';
const ASSETS = [
    './',
    './index.html',
    'https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css'
];

self.addEventListener('install', (event) => {
    event.waitUntil(
        caches.open(CACHE_NAME).then((cache) => cache.addAll(ASSETS))
    );
});

self.addEventListener('fetch', (event) => {
    event.respondWith(
        caches.match(event.request).then((response) => {
            return response || fetch(event.request);
        })
    );
});