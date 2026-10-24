F&O SMART MONEY PRO — Groww API + Options Greeks Android Project

এই ZIP হলো Android Studio source project; সরাসরি install করার APK নয়।

নতুন Options Greeks:
- Delta, Theta, Gamma, Vega এবং Black–Scholes theoretical premium
- CALL/PUT, spot, strike, expiry days, IV এবং risk-free rate input
- বাজারের option premium দিলে theoretical premium-এর সঙ্গে তুলনা

গুরুত্বপূর্ণ: Greeks calculator-এ option chain/IV স্বয়ংক্রিয়ভাবে আসে না। Spot, strike, expiry days এবং IV নিজে দিতে হবে। এটি Black–Scholes আনুমানিক হিসাব; live exchange Greeks নয়। Dividends, volatility skew, market microstructure, taxes/charges বিবেচিত নয়।

Groww API ব্যবহার:
1. Groww অ্যাকাউন্টে Trading API subscription/permission সক্রিয় করুন।
2. Groww Trading APIs থেকে নিজের Access Token তৈরি করুন।
3. অ্যাপ চালিয়ে নিজের ফোনে Access Token লিখুন।
4. Futures Trading Symbol হিসেবে Groww instruments CSV-তে থাকা সঠিক NSE FNO symbol দিন।
5. Live Futures Quote বোতাম চাপুন। Quote থেকে last price, volume, open_interest, previous_open_interest auto-fill হবে, যদি API response-এ থাকে।

নিরাপত্তা:
- API token চ্যাটে বা অন্য কারও সঙ্গে শেয়ার করবেন না।
- Token প্রতিদিন expire হতে পারে; Groww docs যাচাই করুন।
- অ্যাপ শুধু quote পড়ে; অটো অর্ডার দেয় না।
- EMA 9, EMA 20, RSI, previous price এবং average volume এখনও manual inputs।
- এই Futures quote endpoint Options chain বা option IV স্বয়ংক্রিয়ভাবে সরবরাহ করছে না।

APK build:
1. Android Studio-তে এই folder open করুন.
2. Gradle Sync করুন.
3. Build > Build Bundle(s) / APK(s) > Build APK(s).
4. app/build/outputs/apk/debug/app-debug.apk

Official docs:
https://groww.in/trade-api/docs
https://groww.in/trade-api/docs/curl/live-data

কম্পিউটার ছাড়া Android মোবাইল থেকে GitHub Actions দিয়ে APK তৈরি:
1. GitHub-এ সাইন ইন/অ্যাকাউন্ট খুলুন এবং New repository তৈরি করুন (Public বা Private)।
2. ZIP extract করে ভেতরের FNO_Smart_Money_Pro_API_Android ফোল্ডারের সব ফাইল repository-তে upload করুন। .github ফোল্ডারটিও অবশ্যই upload করবেন।
3. Upload শেষে repository-র Actions ট্যাবে যান। প্রথমবার চাইলে “I understand my workflows, go ahead and enable them” চাপুন।
4. “Build Android APK” workflow খুলে “Run workflow” চাপুন। (main/master branch-এ push করলেও workflow চলবে।)
5. কাজ শেষ হলে সবুজ টিক দেখাবে। সেই run-এর পৃষ্ঠার নিচে Artifacts থেকে “FNO-Smart-Money-Pro-debug-APK” ডাউনলোড করুন। ZIP খুলে app-debug.apk ফাইলটি Android-এ ইনস্টল করুন।
6. ইনস্টল করার আগে Android-এর অনুমতি চাইলে শুধু বিশ্বস্ত নিজের তৈরি APK-এর জন্য অনুমতি দিন।

সমস্যা হলে Actions-এর failed run খুলে build log দেখুন। GitHub-এ Access Token, API key বা password কখনও repository-তে upload করবেন না। APK হলো debug build; Play Store release/signed APK নয়।
